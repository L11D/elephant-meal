"""Automatic migration

Revision ID: f53576b6d8e6
Revises: 6729b57f3b4b
Create Date: 2024-06-26 21:07:49.670427

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'f53576b6d8e6'
down_revision: Union[str, None] = '6729b57f3b4b'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
