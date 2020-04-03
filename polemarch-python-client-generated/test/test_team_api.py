# coding: utf-8

"""
    Polemarch

     ### Polemarch is ansible based service for orchestration infrastructure.  * [Documentation](http://polemarch.readthedocs.io/) * [Issue Tracker](https://gitlab.com/vstconsulting/polemarch/issues) * [Source Code](https://gitlab.com/vstconsulting/polemarch)    # noqa: E501

    OpenAPI spec version: v2
    
    Generated by: https://github.com/swagger-api/swagger-codegen.git
"""


from __future__ import absolute_import

import unittest

import polemarch_client
from polemarch_client.api.team_api import TeamApi  # noqa: E501
from polemarch_client.rest import ApiException


class TestTeamApi(unittest.TestCase):
    """TeamApi unit test stubs"""

    def setUp(self):
        self.api = polemarch_client.api.team_api.TeamApi()  # noqa: E501

    def tearDown(self):
        pass

    def test_team_add(self):
        """Test case for team_add

        """
        pass

    def test_team_copy(self):
        """Test case for team_copy

        """
        pass

    def test_team_edit(self):
        """Test case for team_edit

        """
        pass

    def test_team_get(self):
        """Test case for team_get

        """
        pass

    def test_team_list(self):
        """Test case for team_list

        """
        pass

    def test_team_remove(self):
        """Test case for team_remove

        """
        pass

    def test_team_set_owner(self):
        """Test case for team_set_owner

        """
        pass

    def test_team_update(self):
        """Test case for team_update

        """
        pass

    def test_team_user_add(self):
        """Test case for team_user_add

        """
        pass

    def test_team_user_change_password(self):
        """Test case for team_user_change_password

        """
        pass

    def test_team_user_copy(self):
        """Test case for team_user_copy

        """
        pass

    def test_team_user_edit(self):
        """Test case for team_user_edit

        """
        pass

    def test_team_user_get(self):
        """Test case for team_user_get

        """
        pass

    def test_team_user_list(self):
        """Test case for team_user_list

        """
        pass

    def test_team_user_remove(self):
        """Test case for team_user_remove

        """
        pass

    def test_team_user_settings_add(self):
        """Test case for team_user_settings_add

        A settings object, that allows API settings to be accessed as properties.     For example:  # noqa: E501
        """
        pass

    def test_team_user_settings_get(self):
        """Test case for team_user_settings_get

        A settings object, that allows API settings to be accessed as properties.     For example:  # noqa: E501
        """
        pass

    def test_team_user_settings_remove(self):
        """Test case for team_user_settings_remove

        A settings object, that allows API settings to be accessed as properties.     For example:  # noqa: E501
        """
        pass

    def test_team_user_update(self):
        """Test case for team_user_update

        """
        pass


if __name__ == '__main__':
    unittest.main()