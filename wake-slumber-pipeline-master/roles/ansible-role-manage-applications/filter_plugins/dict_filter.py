#!/usr/bin/python
class FilterModule(object):
    def filters(self):
        return {
            'lstest_dict': self.lstest_dict,
            'sort_dict': self.sort_dict,
            'sort_nested_dict': self.sort_nested_dict,
            'filter_dict': self.filter_dict
        }

    def lstest_dict(self, dict_to_sort, sorting_key):
        return sorted(dict_to_sort, key=lambda k: k[sorting_key], reverse=True)[0]
    
    def sort_dict(self, dict_to_sort, sorting_key):
        return sorted(dict_to_sort, key=lambda k: k[sorting_key], reverse=True)
    
    def sort_nested_dict(self, dict_to_sort, sorting_key):
        return sorted(dict_to_sort, key=lambda k: k['tags'][sorting_key], reverse=False)
    
    def filter_dict(self, dict_to_filter, sorting_key):
        return dict_to_filter[sorting_key]