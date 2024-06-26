"""Automatic migration

Revision ID: d2842d7bf5d4
Revises: 35a10106b992
Create Date: 2024-06-26 21:59:02.912463

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'd2842d7bf5d4'
down_revision: Union[str, None] = '35a10106b992'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
