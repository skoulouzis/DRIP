def write_invenory_file(vms):
    try:
        tosca_folder_path = os.path.join(tempfile.gettempdir(), "ansible_files", str(input_current_milli_time()))
    except NameError:
        import sys
        millis = int(round(time.time() * 1000))
        tosca_folder_path = os.path.dirname(os.path.abspath(sys.argv[0])) + os.path.join(tempfile.gettempdir(),
                                                                                         "planner_files",
                                                                                         str(millis))

    pass


def run(interfaces,vms):
    write_invenory_file(vms)
    return None