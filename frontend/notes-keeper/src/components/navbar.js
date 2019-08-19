import React from 'react'

export default class Navbar extends React.Component {
  handleClick () {

  }

  render () {
    return (
      <nav className="navbar">
        <h1>Note Maker</h1>
        <div className="navbar-buttons">
          <button onClick={() => handleClick()}></button>
        </div>
      </nav>
    )
  }
}
