import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Carousel } from 'react-bootstrap';
import image1 from "../../images/CartShare1.jpg"
import image2 from "../../images/CartShare2.jpg"
import image3 from "../../images/CartShare3.png"

class LandingPage extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        if (this.props.auth.isAuthenticated) {
            if (this.props.auth.user.email_verified) {
                this.props.history.push("/home");
            }
        }
    }

    render() {
        return (
            <div className="container valign-wrapper">
                <center>
                    <Carousel>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src={image1}
                                height={700}
                                alt="Cart Share"
                            />
                        </Carousel.Item>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src={image2}
                                height={700}
                                alt="Cart Share"
                            />
                        </Carousel.Item>
                        <Carousel.Item>
                            <img
                                className="d-block w-100"
                                src={image3}
                                alt="Cart Share"
                            />
                        </Carousel.Item>
                    </Carousel>
                </center>
            </div>
        );
    }
}

LandingPage.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(LandingPage));