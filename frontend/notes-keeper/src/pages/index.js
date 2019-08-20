import React from 'react'
import DataTable from '../DataTable'
import Note from '../components/note'
import Send from '../components/send'
import Navbar from '../components/navbar'
import Grid from '@material-ui/core/Grid'
import Container from '@material-ui/core/Container';

export default class IndexPage extends React.Component {
  render () {
    console.log(this)
    return (
      <React.Fragment>
        <Navbar />

        <Container fixed>
          <Grid container maxWidth="sm" spacing={3}>
            <Grid item xs={12}>
            </Grid>

            <Grid item xs={12} display="center">
              <Send />
              <Note />
            </Grid>

            <Grid item xs={12}>
              <DataTable notes={this.props.notes} />
            </Grid>
          </Grid>
        </Container>
      </React.Fragment>
    )
  }
}
