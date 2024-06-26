"""Automatic migration

Revision ID: f6dfa0c366f5
Revises: b0ea5d67d71b
Create Date: 2024-06-26 21:27:11.398411

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'f6dfa0c366f5'
down_revision: Union[str, None] = 'b0ea5d67d71b'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
