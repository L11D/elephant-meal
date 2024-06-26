"""Automatic migration

Revision ID: a74de7b1f4ed
Revises: 77f132dd3265
Create Date: 2024-06-26 21:17:00.786443

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = 'a74de7b1f4ed'
down_revision: Union[str, None] = '77f132dd3265'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
