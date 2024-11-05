import Banner from '@pageComponent/home/banner'
import Widget from '@pageComponent/home/widget'

import styles from './home.module.scss'
import BookCategory from "@pages/bookCategory";

const Home = () => {
  return (
    <div className={styles.wrapper}>
      {/*<Search/>*/}
      <div className={styles.content}>
        <Banner />
        <Widget />
        {/*<BookCategory />*/}
      </div>
    </div>
  )
}

export default Home