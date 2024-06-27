import enum
from logging.config import fileConfig
from sqlalchemy import engine_from_config, pool, create_engine, MetaData, text
from sqlalchemy.orm import sessionmaker
from alembic import context
from backend.Domain.db_config import Base, engine
from backend.env_variables import DB_USER, DB_PASSWORD, DB_HOST, DB_PORT, DB_NAME
from sqlalchemy import inspect, Enum
from sqlalchemy.dialects import postgresql
from alembic import op

DATABASE_URL = f'postgresql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}'

# Load Alembic configuration from alembic.ini file
config = context.config
section = config.config_ini_section
config.set_section_option(section, "DATABASE_URL", DATABASE_URL)
fileConfig(config.config_file_name)
flag = False
import sqlalchemy as sa
# Define the metadata object for your database
target_metadata = Base.metadata


def get_all_enums(metadata):
    enums = {}
    for table in metadata.tables.values():
        for column in table.columns:
            if isinstance(column.type, Enum):
                enums[column.type.name] = [e.name for e in column.type.enum_class]
    return enums
def create_or_upgrade_enum_type(connection, enum_name, new_values):
    inspector = inspect(connection)
    enums = inspector.get_enums()
    # print("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL")
    # print(enums)
    existing_values = set()
    enum_exists = False

    for enum in enums:
        if enum['name'] == enum_name:
            existing_values = set(enum['labels'])
            enum_exists = True
            break

    if not enum_exists:
        try:
            # Создаем ENUM только если его не существует
            new_enum_values_str = ", ".join(f"'{v}'" for v in new_values)
            connection.execute(text(f"CREATE TYPE {enum_name} AS ENUM ({new_enum_values_str})"))
            print(f"Created enum '{enum_name}' with values: {new_values}")
        except Exception as e:
            print(f"Error creating enum '{enum_name}': {e}")

    for value in new_values:
        if value not in existing_values:
            try:
                op.execute("ALTER TYPE enum_type_name ADD VALUE 'new_value'")
                connection.execute(text(f"ALTER TYPE {enum_name} ADD VALUE '{value}'"))
                flag = True
                print(f"Added value '{value}' to enum '{enum_name}'")
            except Exception as e:
                print(f"Error adding value '{value}' to enum '{enum_name}': {e}")

    values_to_remove = existing_values - set(new_values)
    if values_to_remove:
        temp_enum_name = f"{enum_name}_temp"
        new_enum_values_str = ", ".join(f"'{v}'" for v in new_values)

        connection.execute(text(f"CREATE TYPE {temp_enum_name} AS ENUM ({new_enum_values_str})"))

        for table_name, column_name in get_enum_columns(connection, enum_name):
            connection.execute(text(f"ALTER TABLE {table_name} ALTER COLUMN {column_name} TYPE {temp_enum_name} USING {column_name}::text::{temp_enum_name}"))

        connection.execute(text(f"DROP TYPE {enum_name}"))
        connection.execute(text(f"ALTER TYPE {temp_enum_name} RENAME TO {enum_name}"))
        flag = True

def get_enum_columns(connection, enum_name):
    query = """
        SELECT 
            table_name, 
            column_name 
        FROM 
            information_schema.columns 
        WHERE 
            udt_name = :enum_name
    """
    result = connection.execute(text(query), {'enum_name': enum_name})
    return result.fetchall()

def process_enums(connection):
    all_enums = get_all_enums(target_metadata)

    for enum_name, enum_values in all_enums.items():
        create_or_upgrade_enum_type(connection, enum_name, enum_values)

def run_migrations_offline():
    url = config.get_main_option("sqlalchemy.url")
    context.configure(
        url=url, target_metadata=target_metadata, literal_binds=True
    )

    with context.begin_transaction():
        context.run_migrations()

def run_migrations_online():
    connectable = engine_from_config(
        config.get_section(config.config_ini_section),
        prefix="sqlalchemy.",
        poolclass=pool.NullPool,
    )

    with connectable.connect() as connection:
        context.configure(
            connection=connection,
            target_metadata=target_metadata
        )

        with context.begin_transaction():
            try:
                process_enums(connection)
            except Exception as e:
                print(f"Error nothing to change: {e}")
            print(f"FFFFFFFFFFFFFFF {flag}")
            context.run_migrations()

if context.is_offline_mode():
    run_migrations_offline()
else:
    run_migrations_online()
