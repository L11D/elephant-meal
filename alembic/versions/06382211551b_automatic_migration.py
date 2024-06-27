"""Automatic migration

Revision ID: 06382211551b
Revises: d2d3574afcfd
Create Date: 2024-06-27 07:25:30.923487

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '06382211551b'
down_revision: Union[str, None] = 'd2d3574afcfd'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.add_column('ingredients_and_products', sa.Column('chance', sa.Float(), nullable=False))
    # ### end Alembic commands ###


def downgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_column('ingredients_and_products', 'chance')
    # ### end Alembic commands ###
