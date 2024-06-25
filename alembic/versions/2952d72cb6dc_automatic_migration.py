"""Automatic migration

Revision ID: 2952d72cb6dc
Revises: 077ae1d03e47
Create Date: 2024-06-26 04:50:25.174362

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '2952d72cb6dc'
down_revision: Union[str, None] = '077ae1d03e47'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
