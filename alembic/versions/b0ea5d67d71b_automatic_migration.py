"""Automatic migration

Revision ID: b0ea5d67d71b
Revises: ec409e38a253
Create Date: 2024-06-26 21:24:44.699117

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'b0ea5d67d71b'
down_revision: Union[str, None] = 'ec409e38a253'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
