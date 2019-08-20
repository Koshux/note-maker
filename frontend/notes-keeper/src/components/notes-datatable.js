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
  const [state, setState] = React.useState({
    columns: [
      { title: 'Description', field: 'description', sorting: false  },
      { title: 'Creation Date', field: 'creationDate', type: 'datetime' },
    ],
    data: [
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() },
      { description: 'Mehmet', creationDate: new Date() }
    ],
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

  return (
    <MaterialTable
      title="Notes"
      // data={state.data}
      data={state.data}
      icons={tableIcons}
      columns={state.columns}
      options={{
        add: false,
        pageSize: 10,
        update: false,
        search: false,
        pageSizeOptions: [10]
      }}
    />
  )
}
