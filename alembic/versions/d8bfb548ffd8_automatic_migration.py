"""Automatic migration

Revision ID: d8bfb548ffd8
Revises: df371b40019b
Create Date: 2024-06-26 21:32:55.095673

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'd8bfb548ffd8'
down_revision: Union[str, None] = 'df371b40019b'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
