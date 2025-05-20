const getMe = async (checkOnly = false) => {
  
  if(localStorage.getItem('token')){
    const response = await fetch(`${import.meta.env.VITE_API}user/profile`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
  
    return checkOnly ? response.ok : await response.json() 
  } else{
    return
  }
}

export default getMe
