# coding: utf-8

import sys
from setuptools import setup, find_packages

NAME = "nl.sne.sure_tosca"
VERSION = "1.0.0"

# To install the library, run the following
#
# python setup.py install
#
# prerequisite: setuptools
# http://pypi.python.org/pypi/setuptools

REQUIRES = ["connexion"]

setup(
    name=NAME,
    version=VERSION,
    description="SURE TOSCA",
    author_email="S.Koulouzis@uva.nl",
    url="",
    keywords=["Swagger", "SURE TOSCA"],
    install_requires=REQUIRES,
    packages=find_packages(),
    package_data={'': ['swagger/swagger.yaml']},
    include_package_data=True,
    entry_points={
        'console_scripts': ['nl.sne.sure_tosca=nl.sne.sure_tosca.__main__:main']},
    long_description="""\
    SURE Simple qUeRy sErvice TOSCA. 
    """
)

