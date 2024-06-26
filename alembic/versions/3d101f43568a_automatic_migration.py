"""Automatic migration

Revision ID: 3d101f43568a
Revises: a74de7b1f4ed
Create Date: 2024-06-26 21:20:32.941490

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '3d101f43568a'
down_revision: Union[str, None] = 'a74de7b1f4ed'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
