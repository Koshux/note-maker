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
        data={query => Utils.findAllNotes(query)}
        data-tableid="notes-table"
      />
    )
  }
}
