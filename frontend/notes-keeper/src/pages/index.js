import React from 'react'
import Note from '../components/note'
import Send from '../components/send'
import Navbar from '../components/navbar'
import Grid from '@material-ui/core/Grid'
import Container from '@material-ui/core/Container'
import DataTable from '../components/notes-datatable'

export default function IndexPage (props) {
  const [data, setData] = React.useState()

  return (
    <>
      <Navbar />

      <Container fixed>
        <Grid container spacing={3}>
          <Grid item xs={12}></Grid>

          <Grid item xs={10}>
            <Note note={data} setData={setData}/>
          </Grid>

          <Grid item xs={2}>
            <Send note={data} setData={setData} />
          </Grid>

          <Grid item xs={12}>
            <DataTable notes={props.notes} />
          </Grid>
        </Grid>
      </Container>
    </>
  )
}
