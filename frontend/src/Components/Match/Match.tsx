import {Button, Col, Container, Form, Row, Table} from "react-bootstrap";
import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";


interface MatchDTO {
    id: number
    teamBlue : Team
    teamRed : Team
    scoreBlueTeam : number
    scoreRedTeam : number
    datumtijd : string
    spelers : SpelerMatchDTO[]
}

interface SpelerMatchDTO {
    spelerid: number
    teamid: number
}


interface Speler {
    id: number;
    actief: boolean;
    adresDTO: {
        id: number;
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    userDTO: {
        id: number;
        achternaam: string;
        email: string;
        role: string;
        username: string;
        voornaam: string;
    }
}

interface Team {
    id : number
    naam : string
    actief : boolean
    spelerDTO : SpelerDTO[]
    managerDTO : {
        id: number
        userDTO: {
            username: string
        }
    }
}

interface SpelerDTO{
    id : number
    reserve : boolean
    spelerid: number
}

const Match = () => {

    let navigate = useNavigate();
    let params = useParams();
    const getMatchURL = "http://localhost:8080/api/matches/" + params.id
    const [match, setMatch] = React.useState<MatchDTO>({
        spelers: [],
        datumtijd: "",
        id: 0,
        scoreBlueTeam: 0,
        scoreRedTeam: 0,
        teamBlue:  {
            id : 0,
            naam : "",
            actief : false,
            spelerDTO : [],
            managerDTO : {
                id: 0,
                userDTO: {
                    username: ""
                }
            }
        },
        teamRed:  {
            id : 0,
            naam : "",
            actief : false,
            spelerDTO : [],
            managerDTO : {
                id: 0,
                userDTO: {
                    username: ""
                }
            }
        }});

    //get all spelers
    const getAllSpelersURL = "http://localhost:8080/api/spelers";
    const [allSpelers, setAllSpelers] = React.useState<Speler[]>([]);

    React.useEffect(() => {
        axios.get<MatchDTO>(getMatchURL, {
            withCredentials: true
        }).then((response) =>{
            console.log(response.data);
            setMatch(response.data);
        }).catch((e) => {
            console.log(e);
        });

        getSpelers();
        console.log(match)
    }, []);

    const getSpelers = () => {
        axios.get<Speler[]>(getAllSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setAllSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }

    const isReserve = (spelers : SpelerDTO | undefined) => {
        if(spelers?.reserve){
            return "ja"
        }
        return "nee";
    }

    // de onChange functie voor speler
    const ChangeHandlerScore = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setMatch(prevMatch => ({
                ...prevMatch,
                [event.target.name]: event.target.value
            })
        )
    }


    const PostScoreURL = "http://localhost:8080/api/matches/matchresult";
    const addScore = () => {
        axios.put<MatchDTO>(PostScoreURL, match ,{
            withCredentials: true
        }).then((response) =>{
            console.log(response)
        }).catch((e) => {
            console.log(e);
        });
    }

    return (
        <>
            <Container className="bg-dark text-white-50">
                <h1>{match.id}</h1>

                <Row>
                    <Col>
                        {/* selected blue team*/}
                        <h1>blue team</h1>
                        <h2>{match.teamBlue.naam}</h2>
                        <h2>manager : {match.teamBlue.managerDTO.userDTO.username} </h2>
                        <br/>
                        <h2>Spelers : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        match.teamBlue.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && !match.spelers.some(sp => sp.spelerid === speler.id && (sp.teamid === match.teamBlue.id)) ?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(match.teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                            </tr>
                                            : ""
                                    )
                                )
                            }
                            </tbody>
                        </Table>



                    </Col>
                    <Col>
                        {/* selected red team*/}
                        <h1>red team</h1>
                        <h2>{match.teamRed.naam}</h2>
                        <h2>manager : {match.teamRed.managerDTO.userDTO.username} </h2>

                        <br/>
                        <h2>Spelers : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        match.teamRed.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && match.spelers.some(sp => sp.spelerid === speler.id && sp.teamid === match.teamRed.id) ?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(match.teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                            </tr>
                                            : ""
                                    )
                                )
                            }
                            </tbody>
                        </Table>


                    </Col>
                </Row>

                <Row>
                    <Col>
                        <Form.Group className={"mb-3"}>
                            <Form.Label>Score Blue team</Form.Label>
                            <Form.Control name="scoreBlueTeam"
                                          type={"text"}
                                          placeholder="Score"
                                          value={match.scoreBlueTeam}
                                          onChange={ChangeHandlerScore}
                            />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group className={"mb-3"}>
                            <Form.Label>Score Red team</Form.Label>
                            <Form.Control name="scoreRedTeam"
                                          type={"text"}
                                          placeholder="Score"
                                          value={match.scoreRedTeam}
                                          onChange={ChangeHandlerScore}
                            />
                        </Form.Group>
                    </Col>
                </Row>

                <Button onClick={addScore}>set Score</Button>

                <Button onClick={() => navigate("/Matches")}>
                    Terug
                </Button>
            </Container>
        </>
    )
}

export default Match