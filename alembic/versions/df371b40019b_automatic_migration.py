"""Automatic migration

Revision ID: df371b40019b
Revises: 8b789cc0394b
Create Date: 2024-06-26 21:29:06.504566

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'df371b40019b'
down_revision: Union[str, None] = '8b789cc0394b'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
