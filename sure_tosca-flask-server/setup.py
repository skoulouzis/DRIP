# coding: utf-8

import sys
from setuptools import setup, find_packages

NAME = "sure_tosca"
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
    description="tosca-sure",
    author_email="S.Koulouzis@uva.nl",
    url="",
    keywords=["Swagger", "tosca-sure"],
    install_requires=REQUIRES,
    packages=find_packages(),
    package_data={'': ['swagger/swagger.yaml']},
    include_package_data=True,
    entry_points={
        'console_scripts': ['sure_tosca=sure_tosca.__main__:main']},
    long_description="""\
    TOSCA Simple qUeRy sErvice (SURE). 
    """
)

