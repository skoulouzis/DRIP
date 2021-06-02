# coding: utf-8

"""
    tosca-sure

    TOSCA Simple qUeRy sErvice (SURE).   # noqa: E501

    OpenAPI spec version: 1.0.0
    Contact: S.Koulouzis@uva.nl
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


from setuptools import setup, find_packages  # noqa: H301

NAME = "sure_tosca_client"
VERSION = "1.0.0"
# To install the library, run the following
#
# python setup.py install
#
# prerequisite: setuptools
# http://pypi.python.org/pypi/setuptools

REQUIRES = [
"certifi==2019.11.28",
"six==1.14.0",
# "python_dateutl==2.5.3",
"setuptools==46",
"urllib3==1.26.5",
"numpy==1.18.2"
]
    

setup(
    name=NAME,
    version=VERSION,
    description="tosca-sure",
    author_email="S.Koulouzis@uva.nl",
    url="",
    keywords=["Swagger", "tosca-sure"],
    install_requires=REQUIRES,
    packages=find_packages(),
    include_package_data=True,
    long_description="""\
    TOSCA Simple qUeRy sErvice (SURE).   # noqa: E501
    """
)
