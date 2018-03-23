package geb.pages

import geb.Page

class SearchResultPage extends Page {

    static url = '/search'

    static at  = {
        $('h2', 0).text().startsWith('Wyniki wyszukiwania')
    }

    static content = {

        resultList {
            $('ul.collection')
        }
        results {
            resultList.find('li')
        }
    }
}

