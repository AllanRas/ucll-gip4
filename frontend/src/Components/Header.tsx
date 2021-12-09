import 'bootstrap/dist/css/bootstrap.css'
import {Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import {Link} from "react-router-dom";
import axios from "axios";
import {useState} from "react";


const Header = () => {
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
            console.log("Logged out");
        })
    };

    return(
        /** de navigation bar  tutorial: https://www.youtube.com/watch?v=-HEjsVkfjOk **/
        <Navbar bg={"dark"} variant={"dark"}
            sticky={"top"} expand={"sm"} >
            <Container>
                <Navbar.Brand as={Link} to={"/"} style={{marginLeft: "1rem"}}>
                    GIP 4
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
                        <NavDropdown title={"exampleDropdown"} menuVariant={"dark"}>
                            <NavDropdown.Item>
                                <Nav.Link as={Link} to={"/example1"}>example1</Nav.Link>
                            </NavDropdown.Item>
                            <NavDropdown.Item>
                                <Nav.Link as={Link} to={"/example2"}>example2</Nav.Link>
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Nav >
                                <Nav.Link as={Link} to={"/Login"}>Login</Nav.Link>

                                <Nav.Link onClick={logout} >Logout</Nav.Link>


                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>

    )
}

export default Header;