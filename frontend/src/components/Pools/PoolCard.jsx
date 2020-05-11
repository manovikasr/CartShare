import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button, Row, Col } from "react-bootstrap";
import poolImage from "../../images/PoolImage.jpeg";
import ApplyPoolModal from "./ApplyPoolModal";

class PoolCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    };

    render() {
        const pool = this.props.pool;
        var actionButton;
        if (!this.props.alreadyPoolMember) {
            if (pool.user.length === 4) {
                actionButton = (
                    <Button variant="secondary">Full</Button>
                );
            }
            else if (!this.props.applied) {
                actionButton = (
                    <Button variant="primary" onClick={this.handleToggle}>Apply</Button>
                );
            } else {
                actionButton = (
                    <Button variant="warning">Applied</Button>
                );
            }
        }

        return (
            <Card bg="white" style={{ width: "50rem", margin: "2%" }}>
                <Row>
                    <Col>
                        <Card.Img style={{ width: "12rem", height: "12rem" }} alt="" src={poolImage} />
                    </Col>
                    <Card.Body>
                        <Card.Title>{pool.pool_name}</Card.Title>
                        <Card.Text>
                            <b>ID: </b>{pool.pool_id}<br />
                            <b>Description: </b>{pool.pool_desc}<br />
                            <b>Neighbourhood: </b>{pool.neighbourhood_name}<br />
                            <b>ZIP: </b>{pool.pool_zip}<br />
                        </Card.Text>
                    </Card.Body>
                    <Col align="right">
                        <br /><br /><br />
                        {actionButton}&nbsp;&nbsp;&nbsp;
                    </Col>
                </Row>
                <ApplyPoolModal pool={pool} showModal={this.state.showModal} onHide={this.handleToggle} getUserApplications={this.props.getUserApplications} />
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
