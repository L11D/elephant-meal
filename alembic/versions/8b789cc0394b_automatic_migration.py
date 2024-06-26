"""Automatic migration

Revision ID: 8b789cc0394b
Revises: f6dfa0c366f5
Create Date: 2024-06-26 21:27:47.674898

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '8b789cc0394b'
down_revision: Union[str, None] = 'f6dfa0c366f5'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
