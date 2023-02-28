#!/usr/bin/python
class FilterModule(object):
    def filters(self):
        return {
            'filter_json': self.filter_json
        }

    def filter_json(self, json_obj, lookup_key):
        return json_obj[lookup_key]