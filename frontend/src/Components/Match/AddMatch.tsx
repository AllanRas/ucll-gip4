import React, {ChangeEvent, useEffect, useState} from "react";
import axios from "axios";
import {Button, Col, Container, Form, Row, Table} from "react-bootstrap";
import DateTimePicker from 'react-datetime-picker';
import DatePicker from 'react-datepicker';


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

interface createMatchDTO {
    teamBlueId: number
    teamRedId: number
    spelers : SpelerMatchDTO[]
    datumtijd: Date
}

interface SpelerMatchDTO {
    spelerid: number
    teamid: number
}

const AddMatch = () => {


    //get all teams
    const getAllTeamsURL = "http://localhost:8080/api/teams/";
    const [allTeams, setAllTeams]   = useState<Team[]>([])

    //set team blue
    const [teamBlue, setTeamBlue] = useState<Team>({
            managerDTO: {id: 0, userDTO: {username: ""}},
            spelerDTO: [],
            actief: false,
            naam: "",
            id: 0
        }
    );

    //set team red
    const [teamRed, setTeamRed] = useState<Team>({
            managerDTO: {id: 0, userDTO: {username: ""}},
            spelerDTO: [],
            actief: false,
            naam: "",
            id: 0
        }
    );

    //set Match
    const addMatchURL = "http://localhost:8080/api/matches/";
    const [match, setMatch] = useState<createMatchDTO>({
        datumtijd: new Date(),
        spelers: [],
        teamBlueId: 0,
        teamRedId: 0
    });

    //get all spelers
    const getAllSpelersURL = "http://localhost:8080/api/spelers";
    const [allSpelers, setAllSpelers] = React.useState<Speler[]>([]);


    useEffect(() => {
        getAllTeams()
        getSpelers()

    },[]);


    //get all teams api
    const getAllTeams = () => {
        axios.get<Team[]>(getAllTeamsURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            console.log(response.data);
            setAllTeams(response.data);
        }).catch(err => {
            console.log(err);
        });
    }

    const getSpelers = () => {
        axios.get<Speler[]>(getAllSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setAllSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }

    const matchAanmaken = async () => {
      await axios.post<createMatchDTO>(addMatchURL, match,{
            withCredentials: true
        }).then((response) =>{
            console.log(response);
        }).catch((e) => {
            console.log(e);
        });
    }

    const resetTeamBlue = (t : Team) => {
        match.spelers = [];
        setMatch(prevMatch => ({...prevMatch ,teamBlueId : t.id}));
        setTeamBlue(t);
    }

    const resetTeamRed = (t : Team) => {
        match.spelers = [];
        setMatch(prevMatch => ({...prevMatch ,teamRedId : t.id}));
        setTeamRed(t);
    }

    const addToSpelerMatch = (spelerid: number, teamid: number) => {
        if(match.spelers.length == 0){
            match.spelers.push({spelerid: spelerid, teamid: teamid})
        }else{
            if(match.spelers.some(speler => speler.spelerid === spelerid)){

            }else{
                match.spelers.push({spelerid: spelerid, teamid: teamid})
            }
        }
        getSpelers();
    }

    const ChangeHandlerMatch = (time: Date) => {
        setMatch(prevMatch => ({
                ...prevMatch,
                datumtijd : time
            })
        )
    }

    const isReserve = (spelers : SpelerDTO | undefined) => {
        if(spelers?.reserve){
            return "ja"
        }
        return "nee";
    }

    const RemoveSpeler = (spelerid : number, teamid: number) => {
        setMatch(prevMatch => ({
                ...prevMatch,
                spelers :  match.spelers.filter(speler => speler.spelerid !== spelerid)
            })
        )
        getSpelers();
    }

    return (
        <>
            <Container className="bg-dark text-white-50">
                <h1 className="altColor">Match aanmaken</h1>
                <br/>
                <Row>
                    <Col>
                        {/* add teams */}
                        <h1>Teams toevoegen</h1>
                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>Team naam</th>
                                <th className="col-md-2"> </th>
                                <th className="col-md-2 "></th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allTeams.map(team => (
                                    team.id !== teamBlue.id && team.id !== teamRed.id ?
                                        <tr key={team.id}>
                                            <td>{team.id}</td>
                                            <td>{team.naam}</td>
                                            <td><Button onClick={(e) => resetTeamBlue(team)}>blue team</Button></td>
                                            <td><Button className="btn-danger" onClick={(e) => resetTeamRed(team)}>red team</Button></td>
                                        </tr> : " "
                                ))
                            }
                            </tbody>
                        </Table>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        {/* selected blue team*/}
                        <h1>blue team</h1>
                        <h2>{teamBlue.naam}</h2>
                        <br/>
                        <h2>Spelers : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                                <th className="col-md-1"> </th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        teamBlue.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && !match.spelers.some(sp => sp.spelerid === speler.id && (sp.teamid === teamRed.id || teamBlue.id)) ?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                                <td><Button className="btn-success" onClick={() => addToSpelerMatch(speler.id, teamBlue.id)} >+</Button></td>
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
                        <h2>{teamRed.naam}</h2>
                        <br/>
                        <h2>Spelers : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                                <th className="col-md-1"> </th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        teamRed.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && !match.spelers.some(sp => sp.spelerid === speler.id && (sp.teamid === teamRed.id || teamBlue.id)) ?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>

                                                <td><Button className="btn-success" onClick={() => addToSpelerMatch(speler.id, teamRed.id)} >+</Button></td>

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
                        {/* selected players blue team*/}
                        <h2>Selectie : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                                <th className="col-md-1"></th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        teamBlue.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && match.spelers.some(sp => sp.spelerid === speler.id && sp.teamid === teamBlue.id) ?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                                <td><Button className="btn-danger" onClick={() => RemoveSpeler(speler.id, teamRed.id)} >-</Button></td>
                                            </tr>
                                            : ""
                                    )
                                )
                            }
                            </tbody>
                        </Table>
                    </Col>
                    <Col>
                        {/* selected players red team*/}

                        <h2>Selectie : </h2>

                        <Table striped bordered hover variant={'dark'}>
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>username</th>
                                <th>reserve</th>
                                <th className="col-md-1"></th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                allSpelers.map(speler => (
                                        teamRed.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) && match.spelers.some(sp => sp.spelerid === speler.id && sp.teamid === teamRed.id)?
                                            <tr key={speler.id}>
                                                <td>{speler.id}</td>
                                                <td>{speler.userDTO.username}</td>
                                                <td>{isReserve(teamBlue.spelerDTO.find(sp => sp.spelerid === speler.id))}</td>
                                                <td><Button className="btn-danger" onClick={() => RemoveSpeler(speler.id, teamRed.id)} >-</Button></td>
                                            </tr>
                                            : ""
                                    )
                                )
                            }
                            </tbody>
                        </Table>
                    </Col>
                </Row>
                <div>
                <h3>Datum en tijd</h3>

                    <DateTimePicker className="bg-danger"
                                    value={match.datumtijd}
                                    onChange={ChangeHandlerMatch}
                                    disableClock={true}
                    />
                </div>
                <br/>
                <Button onClick={() => matchAanmaken()}> Match aanmaken</Button>
            </Container>
        </>
    )

}

export default AddMatch