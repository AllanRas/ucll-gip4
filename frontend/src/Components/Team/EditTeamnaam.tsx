import {useNavigate, useParams} from "react-router-dom";
import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import {Button, Col, Container, Form, Row} from "react-bootstrap";

const EditTeam = () => {
    let navigate = useNavigate();
    let params = useParams();

    const [teamNaam, setTeamNaam] = useState<string>("");

    const postTeamNaamURL = "http://localhost:8080/api/teams/" + params.id + "/update/" + teamNaam

    const PostTeamNaam = async () => {
        await axios.put(postTeamNaamURL, {} , {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            console.log(response.data);
            navigate("/Teams/" + params.id);
        }).catch(err => {
            console.log(err);
        });
    };

    return (
        <>
            <Container className="col-md-5 bg-dark text-white-50">
                <h1>Team naam aanpassen</h1>

                <br/>

                <Form>
                    <Form.Group className={"mb-3"}>
                        <Form.Label>Team naam</Form.Label>
                        <Form.Control name="naam"
                                      type={"text"}
                                      placeholder="Naam"
                                      value={teamNaam}
                                      onChange={(e) => setTeamNaam(prevState => prevState = e.target.value)}
                        />
                    </Form.Group>

                    <Row className="justify-content-center">
                        <Col lg="3">
                            <Button variant={"primary"}  onClick={() => PostTeamNaam()}>
                                Submit
                            </Button>
                        </Col>
                        <Col>
                            <Button onClick={() => navigate("/Teams"+ params.id)}>
                                Cancel
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Container>
        </>
    )
}

export default EditTeam