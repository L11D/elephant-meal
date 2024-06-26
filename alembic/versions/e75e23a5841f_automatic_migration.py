"""Automatic migration

Revision ID: e75e23a5841f
Revises: d8bfb548ffd8
Create Date: 2024-06-26 21:52:46.098518

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'e75e23a5841f'
down_revision: Union[str, None] = 'd8bfb548ffd8'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
