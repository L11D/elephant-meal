"""Automatic migration

Revision ID: 2a313ae21a47
Revises: e75e23a5841f
Create Date: 2024-06-26 21:56:13.350264

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '2a313ae21a47'
down_revision: Union[str, None] = 'e75e23a5841f'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
