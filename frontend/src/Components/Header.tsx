import 'bootstrap/dist/css/bootstrap.css'
import {Container, Nav, Navbar} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import {useState} from "react";


const Header = () => {

    let navigate = useNavigate();
    const loginURL = 'http://localhost:8080/api/login/user'

    const [user] = useState();

    const logout = async () => {
       // een post die error 401 teruggeeft logged de user uit
        await axios.post(loginURL, user ,{
            auth:{
                username: 'logout',
                password: 'logout'
            },
            withCredentials: true
        }).then((response) => {
        }).catch(err => {
            navigate("/Login");
            console.log("Logged out");
        })
    };

    return(
        /** de navigation bar  tutorial: https://www.youtube.com/watch?v=-HEjsVkfjOk **/

        <>
            <Navbar bg={"dark"} variant={"dark"}
                    sticky={"top"} expand={"sm"} >
                <Container>
                    <Navbar.Brand as={Link} to={"/Login"} style={{marginLeft: "1rem"}}>
                        Role : Manager
                    </Navbar.Brand>

                    <Navbar.Toggle />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link as={Link} to={"/Spelers"}>
                                Spelers
                            </Nav.Link>
                            <Nav.Link as={Link} to={"/Teams"}>
                                Teams
                            </Nav.Link>
                            <Nav.Link as={Link} to={"/Matches"}>
                                Matches
                            </Nav.Link>
                        </Nav>

                        <Navbar.Brand as={Link} to={"/Login"} style={{marginLeft: "1rem"}}>
                            Role : Speler
                        </Navbar.Brand>

                        <Nav className="me-auto">
                            <Nav.Link as={Link} to={"/SpelerGegevens"}>
                                Speler gegevens
                            </Nav.Link>
                            <Nav.Link as={Link} to={"/SpelerTeams"}>
                                Teams
                            </Nav.Link>
                            <Nav.Link as={Link} to={"/SpelerMatchHist"}>
                                Matches
                            </Nav.Link>
                        </Nav>

                        <Nav >
                            <Nav.Link as={Link} to={"/Login"}>Login</Nav.Link>

                            <Nav.Link onClick={logout} >Logout</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    )
}

export default Header;