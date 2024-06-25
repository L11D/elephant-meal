"""Automatic migration

Revision ID: dca3d8c47a16
Revises: 817c6deda892
Create Date: 2024-06-26 02:49:56.033739

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'dca3d8c47a16'
down_revision: Union[str, None] = '817c6deda892'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
