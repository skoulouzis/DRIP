# coding: utf-8

"""
    SEMAPHORE

    Semaphore API  # noqa: E501

    OpenAPI spec version: 2.2.0
    
    Generated by: https://github.com/semaphore-api/semaphore-codegen.git
"""


from __future__ import absolute_import

import unittest

import semaphore_client
from semaphore_client.api.authentication_api import AuthenticationApi  # noqa: E501
from semaphore_client.rest import ApiException


class TestAuthenticationApi(unittest.TestCase):
    """AuthenticationApi unit test stubs"""

    def setUp(self):
        self.api = semaphore_client.api.authentication_api.AuthenticationApi()  # noqa: E501

    def tearDown(self):
        pass

    def test_auth_login_post(self):
        """Test case for auth_login_post

        Performs Login  # noqa: E501
        """
        pass

    def test_auth_logout_post(self):
        """Test case for auth_logout_post

        Destroys current session  # noqa: E501
        """
        pass

    def test_user_tokens_api_token_id_delete(self):
        """Test case for user_tokens_api_token_id_delete

        Expires API token  # noqa: E501
        """
        pass

    def test_user_tokens_get(self):
        """Test case for user_tokens_get

        Fetch API tokens for user  # noqa: E501
        """
        pass

    def test_user_tokens_post(self):
        """Test case for user_tokens_post

        Create an API token  # noqa: E501
        """
        pass


if __name__ == '__main__':
    unittest.main()
