"""Automatic migration

Revision ID: 0ff6348e2cf7
Revises: d8d6b5e52eec
Create Date: 2024-06-27 13:28:13.248702

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '0ff6348e2cf7'
down_revision: Union[str, None] = 'd8d6b5e52eec'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    pass
    # ### end Alembic commands ###


def downgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    pass
    # ### end Alembic commands ###
