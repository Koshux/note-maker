import moment from 'moment'
import Utils from './utils'
import React, { forwardRef } from 'react'
import MaterialTable from 'material-table'
import Refresh from '@material-ui/icons/Refresh'
import LastPage from '@material-ui/icons/LastPage'
import FirstPage from '@material-ui/icons/FirstPage'
import ArrowUpward from '@material-ui/icons/ArrowUpward'
import ChevronLeft from '@material-ui/icons/ChevronLeft'
import ChevronRight from '@material-ui/icons/ChevronRight'

const tableIcons = {
  Refresh:
    forwardRef((props, ref) => <Refresh {...props} ref={ref} />),
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

export default class NotesDataTable extends React.Component {
  render () {
    return (
      <MaterialTable
        title="Notes"
        icons={tableIcons}
        tableRef={this.props.materialTableRef}
        columns={[
          { title: 'Description', field: 'description', sorting: false  },
          {
            title: 'Creation Date',
            field: 'creationDate',
            type: 'datetime',
            defaultSort: 'desc'
          }
        ]}
        options={{
          add: false,
          pageSize: 10,
          update: false,
          search: false,
          pageSizeOptions: [10]
        }}
        data={query => new Promise((resolve, reject) => {
          // Find the sort direction.
          const direction = query.orderDirection
          const sort = direction.length === 0
            ? '-creationDate'
            : (direction === 'asc' ? 'creationDate' : '-creationDate')

          // Calculate offset and limit.
          const offset = query.page === 0 ? 0 : query.page * query.pageSize

          // Set the API URL.
          let url = `${Utils.ENDPOINT}?sort=${sort}`
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
        })}
      />
    )
  }
}
