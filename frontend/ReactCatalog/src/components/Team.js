import React, { Component } from "react";
import "react-table/react-table.css";
import "react-confirm-alert/src/react-confirm-alert.css";
import "./Team.css";
import {SERVER_URL} from "../constants";

class Team extends Component {
  constructor(props) {
    super(props);
    this.state = { user: [],
      open: false,
      message: "" };
  }

  handleClose = (event, reason) => {
    this.setState({ open: false });
  };

  componentDidMount() {
    this.fetchLists();
  }

  // Fetch all users
  fetchLists = () => {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "me", {
      headers: { Authorization: token }
    })
        .then(response => response.json())
        .then(responseData => {
          this.setState({
            users: responseData._embedded.users
          });
        })
        .catch(err => console.error(err));
  };

  render() {
    return (
      <div className="d-flex justify-content-center">
        <div center className="team-leader-box p-4 rounded">
          <h3>Developer</h3>
          <div className="m-3 team-leader wow fadeInDown delay-03s">
            <div className="team-leader-shadow">
              <a href="#" />
            </div>
            <img src="img/tim.jpg" alt="" />
            <ul>
              <li>
                <a
                  href="https://app.enhancv.com/share/5886cb7d?utm_medium=growth&utm_campaign=share-resume&utm_source=dynamic"
                  target="_blank"
                >
                  more info
                </a>
              </li>
            </ul>
          </div>
          <h3 className="wow fadeInDown delay-03s">Tihomir Ganev</h3>
          <span className="wow fadeInDown delay-03s">timganev@gmail.com</span>
        </div>
      </div>
    );
  }
}

export default Team;