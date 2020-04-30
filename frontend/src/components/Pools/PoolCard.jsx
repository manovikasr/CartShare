import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button, Row, Col } from "react-bootstrap";
import poolImage from "../../images/PoolImage.jpeg";

class PoolCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    hideModal = () => {
        this.setState({
            showModal: false
        });
    };

    render() {
        const { user } = this.props.auth;
        const pool = this.props.pool;
        var pool_members = "";
        pool.user.forEach(user => {
            pool_members += user.screen_name + ", ";
        })
        pool_members = pool_members.substring(0, pool_members.length-2);
        return (
            <Card bg="white" style={{ width: "50rem", margin: "2%" }}>
                <Row>
                    <Col>
                        <Card.Img style={{ width: "12rem", height: "12rem" }} alt="" src={poolImage} />
                    </Col>
                    <Card.Body>
                        <Card.Title>{pool.pool_name}</Card.Title>
                        <Card.Text>
                            <b>ID: </b>{pool.pool_id}<br/>
                            <b>Description: </b>{pool.pool_desc}<br/>
                            <b>Neighbourhood: </b>{pool.neighbourhood_name}<br />
                            <b>ZIP: </b>{pool.pool_zip}<br />
                        </Card.Text>
                        <Card.Text>
                            <b>Members: </b> {pool_members}
                        </Card.Text>
                    </Card.Body>
                    <Col align="right">
                        <br /><br /><br/>
                        <Button variant="primary" onClick={this.updateStore}>Apply</Button>&nbsp;&nbsp;&nbsp;
                    </Col>
                </Row>
            </Card >
        );
    }
}

PoolCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolCard));
