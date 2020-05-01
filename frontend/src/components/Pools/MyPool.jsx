import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Alert, Card, Button, Row, Col } from "react-bootstrap";
import AddEditPoolModal from "./AddEditPoolModal";
import poolImage from "../../images/PoolImage.jpeg";
import PoolApplications from "./PoolApplications";
import axios from "axios";

class MyPool extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            pool: null,
            error_message: ""
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        this.getPoolData();
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    };

    getPoolData = () => {
        const { user } = this.props.auth;
        axios.get(`user/${user.id}`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        pool: res.data.pool
                    });
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
                this.setState({
                    pool: null
                });
            });
    };

    deletePool = (e) => {
        if (window.confirm("Are you sure you want to delete this pool?")) {
            axios.post(`delete/${this.state.pool.id}`)
                .then(res => {
                    if (res.status === 200) {
                        this.getPoolData();
                    }
                })
                .catch(e => {
                    if (e.response) {
                        this.setState({
                            error_message: e.response.data.message
                        });
                    }
                });
        }
    };

    leavePool = (e) => {
        if (window.confirm("Are you sure you want to leave this pool?")) {
            axios.post(`leave/${this.state.pool.id}/${this.props.auth.user.id}`)
                .then(res => {
                    if (res.status === 200) {
                        this.getPoolData();
                    }
                })
                .catch(e => {
                    if (e.response) {
                        this.setState({
                            error_message: e.response.data.message
                        });
                    }
                });
        }
    };


    render() {
        const { user } = this.props.auth;
        var pool = this.state.pool;
        var poolInfo, actionButton;
        if (!pool) {
            poolInfo = (
                <div>
                    <Alert variant="warning">You are not part of any pool yet.</Alert>
                    <br />
                    <center>
                        <Button variant="success" onClick={this.handleToggle}>Create Pool</Button>
                        <br /><br />OR<br /><br />
                        <Button variant="primary" href="/pool/all">Join Pool</Button>
                    </center>
                </div>
            )
        } else {
            if (pool && pool.pool_leader_id === user.id) {
                actionButton = (
                    <div>
                        <Button variant="primary" onClick={this.handleToggle}>Update Pool</Button>&nbsp;&nbsp;&nbsp;
                        <Button variant="danger" onClick={this.deletePool}>Delete Pool</Button>
                    </div>
                );
            } else {
                actionButton = (
                    <Button variant="warning" onClick={this.leavePool}>Leave Pool</Button>
                );
            }
            poolInfo = (
                <div>
                    <Row>
                        <Card style={{ width: "70rem", height: "16rem", margin: "2%" }}>
                            <Row>
                                <Col>
                                    <Card.Img style={{ width: "18rem", height: "15rem" }} src={poolImage} />
                                </Col>
                                <Card.Body>
                                    <Card.Title><h1>{pool.pool_name}</h1></Card.Title>
                                    <br />
                                    <Card.Text>
                                        <b>Pool ID: </b>{pool.pool_id}
                                    </Card.Text>
                                    <Card.Text>
                                        <b>Description: </b>{pool.pool_desc}
                                    </Card.Text>
                                    <Card.Text>
                                        <b>Neighbourhood: </b>{pool.neighbourhood_name}
                                    </Card.Text>
                                    <Card.Text>
                                        <b>ZIP: </b>{pool.pool_zip}
                                    </Card.Text>
                                </Card.Body>
                            </Row>
                        </Card>
                    </Row>
                    <br />
                    <center>
                        {actionButton}
                    </center>
                    <br />
                    <table class="table" style={{ width: "30%" }}>
                        <thead>
                            <th>
                                Members
                            </th>
                            <th>
                            </th>
                        </thead>
                        <tbody>
                            {
                                pool.user && pool.user.map(member => {
                                    return (
                                        <tr>
                                            <td>
                                                {member.screen_name}
                                            </td>
                                            <td>
                                                {pool.pool_leader_id === member.id && <b>Pool Leader</b>}
                                            </td>
                                        </tr>
                                    )
                                })
                            }
                        </tbody>
                    </table>
                    <br/><br/>
                    <PoolApplications pool={pool}/>
                </div>
            );
        }

        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br />
                {poolInfo}
                <AddEditPoolModal pool={this.state.pool} showModal={this.state.showModal} onHide={this.handleToggle} getPoolData={this.getPoolData} />
            </div>
        );
    }
}

MyPool.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(MyPool));