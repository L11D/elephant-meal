"""Automatic migration

Revision ID: 537fb71f6a97
Revises: 2a313ae21a47
Create Date: 2024-06-26 21:56:52.821137

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '537fb71f6a97'
down_revision: Union[str, None] = '2a313ae21a47'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
