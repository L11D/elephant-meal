"""Automatic migration

Revision ID: 817c6deda892
Revises: 572c458ff7c2
Create Date: 2024-06-26 02:49:27.792079

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '817c6deda892'
down_revision: Union[str, None] = '572c458ff7c2'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
