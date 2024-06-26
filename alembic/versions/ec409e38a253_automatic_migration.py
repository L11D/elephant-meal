"""Automatic migration

Revision ID: ec409e38a253
Revises: 3d101f43568a
Create Date: 2024-06-26 21:21:59.038860

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'ec409e38a253'
down_revision: Union[str, None] = '3d101f43568a'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
