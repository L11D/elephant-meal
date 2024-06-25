"""Automatic migration

Revision ID: 077ae1d03e47
Revises: f3ba254e38ee
Create Date: 2024-06-26 02:59:40.069379

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '077ae1d03e47'
down_revision: Union[str, None] = 'f3ba254e38ee'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
