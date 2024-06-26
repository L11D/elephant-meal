"""Automatic migration

Revision ID: 35a10106b992
Revises: 537fb71f6a97
Create Date: 2024-06-26 21:57:35.188912

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '35a10106b992'
down_revision: Union[str, None] = '537fb71f6a97'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
