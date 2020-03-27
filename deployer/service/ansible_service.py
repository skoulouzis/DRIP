import base64
import json
import logging
import os
import tempfile
from collections import namedtuple
from stat import S_IREAD
from subprocess import Popen, PIPE

import re

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


def execute(nodes_pair):
    target = nodes_pair[0]
    source = nodes_pair[1]
    pass