import {Button, Col, Container, Form, Row, Table} from "react-bootstrap";
import React, {ChangeEvent, useEffect} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import moment from "moment";


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

interface SimpleSpeler {
    id: number;
    actief: boolean;
    userSimpleDTO: {
        id: number;
        role: string;
        username: string;
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

const SpelerMatch = () => {

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
    const getAllSpelersURL = "http://localhost:8080/api/spelers/getAllSimple";
    const [allSpelers, setAllSpelers] = React.useState<SimpleSpeler[]>([]);

    useEffect(() => {
        axios.get<MatchDTO>(getMatchURL, {
            withCredentials: true
        }).then((response) =>{
            setMatch(response.data);
        }).catch((e) => {
            console.log(e);
        });

        getSpelers();
    }, [getMatchURL]);

    const getSpelers = () => {
        axios.get<SimpleSpeler[]>(getAllSpelersURL, {
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
            navigate(-1);
        }).catch((e) => {
            console.log(e);
        });
    }

    if(!allSpelers) return null;

    return (
        <>
            <Container className="bg-dark text-white-50">

                <h2>datum : {moment(match.datumtijd).format('DD-MM-YYYY HH:mm')} </h2>

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
                                <th>username</th>
                                <th>reserve</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        match.spelers.some(sp => sp.spelerid === speler.id && (sp.teamid === match.teamBlue.id)) ?
                                            <tr key={speler.id}>
                                                <td>{speler.userSimpleDTO.username}</td>
                                                <td>{isReserve(match.teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                            </tr>
                                            : null
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
                                <th>username</th>
                                <th>reserve</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        match.spelers.some(sp => sp.spelerid === speler.id && sp.teamid === match.teamRed.id) ?
                                            <tr key={speler.id}>
                                                <td>{speler.userSimpleDTO.username}</td>
                                                <td>{isReserve(match.teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                            </tr>
                                            : null
                                    )
                                )
                            }
                            </tbody>
                        </Table>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <h3>Blue team Score : </h3>
                        <h1>{match.scoreBlueTeam}</h1>
                    </Col>
                    <Col>
                        <h3>Red team Score : </h3>
                        <h1>{match.scoreRedTeam}</h1>
                    </Col>
                </Row>

                <Button onClick={() => navigate(-1)}>
                    Terug
                </Button>
            </Container>
        </>
    )
}

export default SpelerMatch