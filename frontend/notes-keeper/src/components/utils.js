import moment from 'moment'

const headers = {
  cache: 'no-cache',
  headers: { 'Content-Type': 'application/vnd.api+json' }
}

const ENDPOINT = 'lanzonprojects/api/notes'

export default {
  /**
   * JSON:API endpoint for the Notes `POST` and `GET` functionality.
   */
  ENDPOINT,

  /**
   * Async function to create a new note. Will perform a POST request
   */
  createNote: (requestOpts) => {
    // Update the HTTP Method.
    headers.method = 'POST'
    // Update the the payload to be sent.
    headers.body = requestOpts

    // Send the note.
    return fetch(this.ENDPOINT, headers).then(response => response.json())
  },

  /**
   * Async function to retrieve all notes. `query` argument is provided
   * by the `material-table` which is used to generate the correct pagination
   * and sorting values for the subsequent request.
   */
  findAllNotes: (query) => {
    headers.method = 'GET'

    return new Promise((resolve, reject) => {
      // Find the sort direction.
      const direction = query.orderDirection
      const sort = direction.length === 0
        ? '-creationDate'
        : (direction === 'asc' ? 'creationDate' : '-creationDate')

      // Calculate offset and limit.
      const offset = query.page === 0 ? 0 : query.page * query.pageSize

      // Set the API URL.
      let url = `${ENDPOINT}?sort=${sort}`
      url += `&page[offset]=${offset}`
      url += `&page[limit]=${query.pageSize}`

      // Fetch notes.
      fetch(url, headers)
        .then(response => response.json())
        .then(result => {
          // Return the promise to show no records (or due to some failure).
          if (result == null || result.error != null) {
            return resolve({
              data: [],
              page: 0,
              totalCount: 0
            })
          }

          const notes = result.data.reduce((notes, { attributes }) => {
            notes.push({
              description: attributes.description,
              creationDate: moment(attributes.creationDate).format('LLL')
            })

            return notes
          }, [])

          // API must resolve to load notes.
          return resolve({
            data: notes,
            page: query.page,
            totalCount: result.meta.totalResourceCount
          })
        }).catch(err => {
          console.error(err)
          return reject(err)
        })
    })
  }
}
