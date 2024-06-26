"""Automatic migration

Revision ID: 91d89590beb8
Revises: f53576b6d8e6
Create Date: 2024-06-26 21:15:22.465429

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '91d89590beb8'
down_revision: Union[str, None] = 'f53576b6d8e6'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
