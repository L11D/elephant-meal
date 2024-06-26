"""Automatic migration

Revision ID: 77f132dd3265
Revises: 91d89590beb8
Create Date: 2024-06-26 21:16:19.746664

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '77f132dd3265'
down_revision: Union[str, None] = '91d89590beb8'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
