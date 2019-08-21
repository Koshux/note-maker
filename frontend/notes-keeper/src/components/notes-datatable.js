import moment from 'moment'
import React, { forwardRef } from 'react'
import MaterialTable from 'material-table'
import LastPage from '@material-ui/icons/LastPage'
import FirstPage from '@material-ui/icons/FirstPage'
import ArrowUpward from '@material-ui/icons/ArrowUpward'
import ChevronLeft from '@material-ui/icons/ChevronLeft'
import ChevronRight from '@material-ui/icons/ChevronRight'

export default function NotesDataTable(props) {
  // TODO: Use this.props.notes to load remote notes
  // TODO: Ensure that once a new note has been added, it updates the table.
  // TODO: Ensure that pagination works well with JSON:API (fetch remote data).
  // console.log(props.notes)
  const [state] = React.useState({
    columns: [
      { title: 'Description', field: 'description', sorting: false  },
      { title: 'Creation Date', field: 'creationDate', type: 'datetime' },
    ]
  })

  const tableIcons = {
    FirstPage:
      forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
    LastPage:
      forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
    NextPage:
      forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    PreviousPage:
      forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
    SortArrow:
      forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />)
  }

  const headers = {
    method: 'GET',
    cache: 'no-cache',
    headers: { 'Content-Type': 'application/vnd.api+json' }
  }

  return (
    <MaterialTable
      title="Notes"
      icons={tableIcons}
      columns={state.columns}
      options={{
        add: false,
        pageSize: 10,
        update: false,
        search: false,
        pageSizeOptions: [10]
      }}
      data={query => new Promise((resolve, reject) => {
        const offset = query.page === 0 ? 0 : query.page + query.pageSize
        const limit = query.page + query.pageSize

        let url = 'lanzonprojects/api/notes'
        url += `?page[offset]=${offset}`
        url += `&page[limit]=${limit}`

        fetch(url, headers)
          .then(response => response.json())
          .then(result => {
            console.log(result)
            // Return the promise to prevent further execution.
            if (result == null || result.errors != null) {
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
      })}
    />
  )
}
