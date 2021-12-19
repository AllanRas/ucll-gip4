import {useNavigate} from "react-router-dom";
import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import {Button, Col, Container, Form, Row} from "react-bootstrap";

interface CreateTeam {
    id : number
    naam : string
    managerDTO : {
        id: number
    }
    actief: boolean
}

const postTeamURL = "http://localhost:8080/api/teams";

const AddTeam = () => {

    let navigate = useNavigate();


    const [team, setTeam] = useState<CreateTeam>({
            actief: false,
            id: 0,
            managerDTO: {id: 0},
            naam: ""
    }
    );

    const PostTeam = async () => {
        await axios.post<CreateTeam>(postTeamURL, team, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            navigate("/Teams");
        }).catch(err => {
            console.log(err);
        });
    };

    const ChangeHandlerTeam = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setTeam(prevTeam => ({
                ...prevTeam,
                [event.target.name]: event.target.value
            })
        )
    }

    return (
        <>
            <Container className="col-md-5 bg-dark text-white-50">
                <h1>Team Toevoegen</h1>
                <br/>
                <Form>
                    <Form.Group className={"mb-3"}>
                        <Form.Label>Team naam</Form.Label>
                        <Form.Control name="naam"
                                      type={"text"}
                                      placeholder="Naam"
                                      value={team.naam}
                                      onChange={ChangeHandlerTeam}
                        />
                    </Form.Group>
                    <Row className="justify-content-center">
                        <Col lg="3">
                            <Button variant={"primary"}  onClick={() => PostTeam()}>
                                Submit
                            </Button>
                        </Col>
                        <Col>
                            <Button onClick={() => navigate("/Teams")}>
                                Cancel
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Container>
        </>
    )
}

export default AddTeam