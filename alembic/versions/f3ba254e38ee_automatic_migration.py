"""Automatic migration

Revision ID: f3ba254e38ee
Revises: dca3d8c47a16
Create Date: 2024-06-26 02:52:17.668310

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'f3ba254e38ee'
down_revision: Union[str, None] = 'dca3d8c47a16'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
